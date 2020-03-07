package test.base;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(PrependModuleName.class)
class PrependModuleNameTests {

  final PrependModuleName generator = new PrependModuleName();

  @Test
  void generateDisplayNameForClass() {
    assertEquals("test.base/PrependModuleNameTests", generator.generateDisplayNameForClass(getClass()));
  }
}