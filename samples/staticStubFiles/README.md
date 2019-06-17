## Wiremock with statically defined stub

### About application.yml
- File to external configuration for the server. 
    ```
    wm:
      server:
        mappingsFilesDir: /usr/local/var/wm-stub  // sets the directory to read the mappings and __files directory from.
    ``` 

### Dockerfile
- Containerize the module with wiremock server, the stub file and the configuration file.
    ```batch
    FROM shsamkit/extended-wiremock:latest
    ADD ./stubs/mappings /usr/local/var/wm-stub/mappings      // copy the mappings to targetted directory configured in application.yml
    ADD ./stubs/__files /usr/local/var/wm-stub/__files        // copy the __files to targetted directory configured in application.yml
    ADD ./application.yml /usr/local/var/application.yml      // copy the configurations to targetted location, pass the configuration location via command line args.
    ENV CONFIG_OVERRIDES="--spring.config.location=/usr/local/var/application.yml"    // Use environent variable to set location to the external configuration file.
    EXPOSE 8080/tcp     // By default the application starts on 8080. If you set port via the configuration file, make sure to update the exposed port in docker file and run command.
    ```

### How to run 
- Build : `docker build . --tag static-stub-file --rm`
- Run : `docker run -p 8080:8080 static-stub-file`
- Verify: `http://localhost:8080/__admin/mappings` 