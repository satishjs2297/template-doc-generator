package com.westat.ctsu.template;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

public class ProjectScanner {

  public  List<String> getRecursiveClassName(String moduleName, String rootPackage) throws ClassNotFoundException {
    List<String> retval = new ArrayList<>();
    ClassLoader cl = getClass().getClassLoader(); 
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
    MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resolver);
    String basePath = ClassUtils.convertClassNameToResourcePath(rootPackage);
    Resource[] resources;
    try {
      resources = resolver.getResources("classpath*:" + basePath + "/**/*.class");
    } catch (IOException e) {
      throw new AssertionError(e);
    }
    for (Resource resource : resources) {
      MetadataReader reader;
      try {
        reader = readerFactory.getMetadataReader(resource);
      } catch (IOException e) {
        throw new AssertionError(e);
      }
      String className = reader.getClassMetadata().getClassName();
      if(className.contains(moduleName)) {
        Class<?> navigatorClass = Class.forName(className);
        for(Field field : navigatorClass.getDeclaredFields()) {
          String canonicalName = field.getType().getCanonicalName();
          if(canonicalName.contains(rootPackage)) {
            retval.add(field.getType().getCanonicalName());
          }
        }
        retval.add(className);
      }
    }
    return retval;
  }
}