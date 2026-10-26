#  Kaptan Danuj Exception Logger
 
**A zero-configuration, annotation-based exception logger for Spring Boot developers, And for fellow SpringBoot Developers**
 
 
---


 # Note: This Annotation Is Not Vibe Coded And Made For SpringBoot Developers Who Want Clean Message Inside Their Console

 
## Features
 
-  **Zero configuration**: add the annotation and you're done
-  **Custom log message**: tag every failure with your own identifier (e.g. `STUDENT_FETCH_FAILURE`)
-  **Optional stack trace**: turn full stack traces on or off per method
-  **Optional argument logging**: see exactly which input values caused the failure
-  **Built for development**: made for developers who want fast feedback on their console
---
 
## 📦 Installation
 

 
### Maven
 
```xml
<dependency>
            <groupId>io.github.kaptandanuj</groupId>
            <artifactId>anyexception-logger-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```

##  Quick Start
 
Add `@ExceptionLog` on any Spring-managed bean method (`@Service`, `@Component`, `@Repository`, etc.):
 
```java
@Service
public class StudentService {
 
    @ExceptionLog(
        message = "STUDENT_FETCH_FAILURE",
        printStackTrace = false,
        printArgs = true
    )
    public StudentResponseDto getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Student not found with ID: " + id));
    }
}
```

## Sample Output

```
==================== [ STUDENT_FETCH_FAILURE ] ====================
Method    : StudentService.getById(..)
Exception : com.example.exception.ResourceNotFoundException
Message   : Student not found with ID: 999
Arguments : [999]
==========================================================================
```
## BTS WHAT IT USES

### This Uses SLF4J (Simple Logging Facade for Java) Internally
## 🔄 Execution Flow

```text
[ Client Request ]
        │
        ▼
[ Controller ]
        │
        ▼
[ Service Method ] ── (Throws Exception)
        │
        ▼
[ Spring AOP Intercepts ]
        │
        ▼
[ Danuj Logger Starter Triggers ]
        │
        ├── 1. Reads @ExceptionLog (message, printStackTrace, printArgs)
        ├── 2. Extracts Method Name & Arguments via Reflection
        ├── 3. Builds Visual ASCII Banner
        └── 4. Logs via SLF4J (DANUJ-EXCEPTION-LOGGER)
        │
        ▼
[ Exception Propagates Forward ]
        │
        ▼
[ Global Exception Handler ] (Sends response back to Client)
```


 

 
