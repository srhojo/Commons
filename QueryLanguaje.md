# Modulo de búsqueda por QueryLanguage

## ¿Qué es?
Se trata de un modulo  genérico para implementar un protocolo de QueryLanguaje sobre la capa común de "JPA Specification"

Los objetivos principales son:
 - Facilitar su uso al desarrollador.
 - Simplificar la generación de métodos de búsquedas que no requieran cruces de tablas.
 - Especificar una manera canonica de consumir las búsquedas desde un frontal.

## ¿Cómo se ha implementado?
El modulo se ha implementado entorno a una única interfaz que da acceso a toda la funcionalidad
**QueryLanguajeComponent** que hace la labor de convertir un filtro (una cadena de texto) en una "jpa specification"
para que Spring JPA sepa interpretarlo y así realizar las búsquedas.

Las caracteristicas que se pueden usar en el filtro están detalladas más adelante en el documento.

Además entorno al modulo se han creado 3 apartados para controlar la funcionalidad:
- Excepciones propias
- Entidades báse
- Converters

#### Excepciones
Se ha creado la clase **QueryLanguageException** para encapsular las excepciones que se den dentro del modulo.
De esta manera ayudamos a identificar mejor las excepciones que se produzcan.

### Entidades base.
En este apartado se han creado dos entidades propias y una interfaz, para ayudar al manejo de datos, paginación y 
búsqueda.

###### OffsetPagination
Clase que contiene el estado de la paginación en la petición.

```java
class OffsetPagination implements Serializable {  
    Integer limit;
    Long offset;
    Long totalElements;
}
```

###### ContainerList
Clase báse que contiene una lista de datos genéricos y la paginación (OffsetPagination)


```java
public class ContainerList<T> {
    List<T> values;
    OffsetPagination pagination;
}
```

###### SearchableEntity
Interfaz que nos ayuda a identificar si una clase puede ser búscada dentro de otra

```java
public interface SearchableEntity {
}
```


## ¿Cómo se añade la funcionalidad al código?
Para añadir la funcionalidad habrá que modificar los siguientes componentes:
 - Repositorios de JPA
 - Servicio
 - Controllador


#### Repositorios
Los repositorios de JPA deberán extender de la clase "JpaSpecificationExecutor"

```java
public interface ClientRepository extends JpaRepository<ClientEntity, Long>, JpaSpecificationExecutor<ClientEntity> {
}

// llamaremos al método clientRepository.findAll(specification);

```

#### Servicio
En el servicio de negocio habrá que cargar el modulo correspondiente al queryLanguage

```java
@Service
class ClientService {
    
    @Autowired
    QueryLanguajeComponent<ClientEntity> qlClient;

    //La opción de páginación es opcional
    ContainerList<ClientEnity> search(final String filter, final Pageable pageable) {
        final Page<ClientEntity> entityPage = clientDao.search(qlClient.parse(filter), pageable);

        final OffsetPagination offsetPagination = new OffsetPagination(entityPage.getSize(), entityPage.getTotalElements(), entityPage.getTotalElements());

        return ContainerList.of(entityPage.getContent(), offsetPagination);
    }
}
```


#### Controlador
En el controlador se expondrá un método que reciba por parametros un filtro y opcionalmente una paginación.

```java
class ClientController {
    
    @Autowired
    ClientService service;

    @GetMapping(value = "/clients")    
    ContainerList<ClientEntity> findClients(@RequestParam(value = "filter", required = false) final String filter,
                                                             @ApiIgnore final Pageable pageable) {
        return service.search(filter, pageable);
    }
}
```



## ¿Qué funciones implementa?

### Operaciones implementadas

| Operación    | Simbolo | Descripción                             | Ejemplo                    |
|--------------|---------|-----------------------------------------|----------------------------|
| Igual        | ==      | Igualación de argumento                 | id==1                      |
| Negación     | !=      | Diferente de                            | enVenta!=true              |
| Mayor que    | =gt=    | Más grande que (númericos, fechas,...)  | fechaEnVenta=gt=24/03/2020 |
| Menor que    | =lt=    | Más pequeño que (númericos, fechas,...) |                            |
| Contiene | =in=    | Que contenga el valor (Strings)         | nombre=in=piso             |

#### Búsquedas simples
Las búsquedas se podrán hacer por cualquier campo de la entidadad sobre la cual se active el  modulo. 
Un ejemplo
```Java
class ClientEntity { 
    Long id;
    String name;
    String DNI;
    LocalDate birthDate;
}
```

```text
    ?filter=id==1
    ?filter=name=in=Rosa
    ?filter=birthDate=gt=16/6/1990
```


#### Búsquedas complejas
En el filtro de búsqueda se pueden añadir más de un campo de la entidad para realizar dicha búsqueda, estos parametros 
irán separados por el caracter " , " y por defecto se entenderán como agregaciones "and" al filtro.

```text
    ?filter=name=in=Maria,dni==55226655X
```

Si queremos hacer agregaciones de tipo "or" se deberá especificar el caracter " ' " antes del campo de filtro.

```text
    ?filter=name=in=Maria,'name=in=Andrea
```



#### Búsquedas de entidades anidadas
El modulo permite hacer búsquedas en entidades que contengan otras de tipo OneToOne o OneToMany, las entidades que 
quieran esta caracterisica deberán implementar la interfaz "SearchableEntity" y automáticamente se podrá hacer búsquedas
en su entidad anidada por el @Id correspondiente, NO hace falta especificar el id en el filtro de búsqueda y este tampoco
es necesario que sea de tipo Long, o Int, el modulo lo infiere automáticamente 

```Java
//Ejemplo de clases
@Entity
class DeveloperEntity  {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private ProjectEntity project;
}

@Entity
class ProjectEntity implements SearchableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    @Column(name = "name", nullable = false)
    private String name;
}
```
```text
Ejemplo de filtro:
    GET: developers?filter=project==1
```