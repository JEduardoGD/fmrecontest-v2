package mx.fmre.rttycontest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FirstTest  {

	@Test
	void test() {
		Assertions.assertEquals(201, 201);
	}

}
