package es.usj.androidapps.config

import es.usj.androidapps.model.Actor
import es.usj.androidapps.model.Genre
import es.usj.androidapps.model.Movie
import org.h2.tools.Server
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder
import org.hibernate.cfg.Environment
import org.hibernate.tool.hbm2ddl.SchemaExport
import org.hibernate.tool.schema.TargetType
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.io.File
import java.sql.SQLException
import java.util.*
import javax.persistence.ValidationMode
import javax.sql.DataSource


@Configuration
@EnableJpaRepositories(basePackages = ["es.usj.androidapps"])
@EnableTransactionManagement
class H2SQLConfiguration {

    @Value("\${spring.datasource.config.base.package}")
    protected val basePackage: String? = null

    @Value("\${spring.jpa.database-platform}")
    protected val dialect: String? = null

    @Value("\${spring.datasource.user}")
    private val user: String? = null

    @Value("\${spring.datasource.password}")
    private val password: String? = null

    @Bean
    fun dataSource(): DataSource {
        val builder = EmbeddedDatabaseBuilder()
        return builder.setType(EmbeddedDatabaseType.H2)
            .build()
    }

    // Start WebServer, access http://localhost:8082
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Throws(SQLException::class)
    fun startDBManager(): Server {
        return Server.createWebServer()
    }

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean? {
        val props = Properties()
        props["spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults"] = false
        props["hibernate.temp.use_jdbc_metadata_defaults"] = false
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource()
        emf.setPackagesToScan(basePackage)
        val jpaVendorAdapter = HibernateJpaVendorAdapter()
        jpaVendorAdapter.setDatabasePlatform(dialect)
        jpaVendorAdapter.setGenerateDdl(true)
        jpaVendorAdapter.setShowSql(false)
        emf.jpaVendorAdapter = jpaVendorAdapter
        emf.setJpaProperties(props)
        emf.setValidationMode(ValidationMode.AUTO)
        emf.afterPropertiesSet()
        return emf
    }

    private fun generateCustomSchema() {
        val settings: MutableMap<String?, String?> = HashMap()
        settings[Environment.URL] = "jdbc:h2:mem:moviesdb"
        settings[Environment.USER] = user
        settings[Environment.PASS] = password
        val serviceRegistry = StandardServiceRegistryBuilder().applySettings(settings).build()
        val metadataSources = MetadataSources(serviceRegistry)
        metadataSources.addAnnotatedClass(Actor::class.java)
        metadataSources.addAnnotatedClass(Movie::class.java)
        metadataSources.addAnnotatedClass(Genre::class.java)
        val metadata: org.hibernate.boot.Metadata = metadataSources.buildMetadata()

        if (File("../create-db.sql").exists()) {
            File("../create-db.sql").delete()
        }
        val schemaExport = SchemaExport()
        schemaExport.setFormat(true)
        schemaExport.setDelimiter(";")
        schemaExport.setOutputFile("../create-db.sql")
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata)
    }
}