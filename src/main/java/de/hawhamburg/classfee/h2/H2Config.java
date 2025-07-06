package de.hawhamburg.classfee.h2;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * Starts a tcp server so that IntelliJ IDEA can connect to the H2 in-memory database.
 * <p>
 * 1. Remove the line {@code <scope>runtime</scope>} from the h2 dependency in the pom.xml,
 * otherwise {@code org.h2.tools.Server} will be an unknown class.
 * <pre>{@code
 * <dependency>
 *     <groupId>com.h2database</groupId>
 *     <artifactId>h2</artifactId>
 * </dependency>
 * }</pre>
 * After the edit is done, right-click on pom.xml->Maven->Reload project
 * <p>
 * 2. Add the following lines to application.properties located in the resources folder:
 * <pre>{@code
 * spring.datasource.url=jdbc:h2:mem:mydb
 * spring.datasource.driverClassName=org.h2.Driver
 * spring.datasource.username=sa
 * spring.datasource.password=sa
 * spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
 * }</pre>
 * <p>
 * 3. Open View->Tool Windows->Database
 * Add a new "H2 Data Source" via the plus button.
 * Type in the following settings in the dialog window:
 * <pre>{@code
 * Host: localhost
 * Port: 9090
 * Authentication: User & Password
 * User: sa
 * Password: sa
 * Save: Forever
 * Database: mem
 * URL: jdbc:h2:tcp://localhost:9090/mem:mydb
 * }</pre>
 * <p>
 * Note: This only works if the password for the in-memory database is <b>not</b> empty!
 * <p>
 * Open the tab "Schemas" and make sure "Default schema" is activated.
 */
@Configuration
class H2Config {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server inMemoryH2DatabaseaServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9090");
    }
}