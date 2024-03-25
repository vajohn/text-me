package lajabu.john.textme.utilities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
      "EEEE dd MMMM yyyy HH:mm:ss", Locale.ENGLISH);

  @Override
  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeString(value.format(formatter));
  }
}
