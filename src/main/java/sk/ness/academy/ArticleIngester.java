package sk.ness.academy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.service.ArticleService;

import java.io.File;
import java.io.FileReader;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      context.registerShutdownHook();

      final ArticleService articleService = context.getBean(ArticleService.class);

      // Load file with articles and ingest
      String fileText = "";
      try {
        FileReader fileReader = new FileReader("articles_to_ingest.txt");
        int character;
        while((character = fileReader.read())!=-1){
          fileText += (char)character;
        }
        System.out.println(fileText);
      }catch (Exception e){
        System.out.println("Chyba subor");
      }
      articleService.ingestArticles(fileText);
    }
  }
}
