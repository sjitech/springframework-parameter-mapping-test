package hello;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class HelloController {

    static final String localDateTimePattern = "yyyy-MM-dd'T'HH:mm";
    static final String localDateTimeStr = "2009-10-23T22:59";
    static final LocalDateTime localDateTime = LocalDateTime.parse(localDateTimeStr, DateTimeFormatter.ofPattern(localDateTimePattern));

    @RequestMapping("/serveLocalDateTimeWithCustomFormatAndDefaultValue")
    public String serveLocalDateTimeWithCustomFormatAndDefaultValue(
            @RequestParam(required = false, defaultValue = localDateTimeStr)
            @DateTimeFormat(pattern = localDateTimePattern)
                    LocalDateTime v
    ) {
        return v.toString();
    }

    public static enum XxxEnum {
        enum1, enum2
    }

    static final String defaultXxxEnumStr = "enum2";
    static final XxxEnum defaultXxxEnum = XxxEnum.valueOf(defaultXxxEnumStr);

    @RequestMapping("/serveXxxEnumWithDefaultValue")
    public String serveXxxEnumWithDefaultValue(
            @RequestParam(required = false, defaultValue = defaultXxxEnumStr)
                    XxxEnum v
    ) {
        return v.toString();
    }

    public static class Class1 {
        LocalDateTime ldt;

        public LocalDateTime getLdt() {
            return ldt;
        }

        //@JsonFormat(pattern = localDateTimePattern)
        public void setLdt(LocalDateTime ldt) {
            this.ldt = ldt;
        }

        Class2 innerClassInstance;

        public Class2 getInnerClassInstance() {
            return innerClassInstance;
        }

        public void setInnerClassInstance(Class2 innerClassInstance) {
            this.innerClassInstance = innerClassInstance;
        }
    }

    public static class Class2 {
        LocalDateTime ldt;

        public LocalDateTime getLdt() {
            return ldt;
        }

        //@JsonFormat(pattern = localDateTimePattern)
        public void setLdt(LocalDateTime ldt) {
            this.ldt = ldt;
        }
    }

    @RequestMapping("/serveObjectWithCustomDateTimeFormat")
    public String serveObjectWithCustomDateTimeFormat(@RequestBody Class1 v) {
        return v.getLdt().toString() + ' ' + v.getInnerClassInstance().getLdt().toString();
    }

    public static class Class3 {
        Long hyphenNameProperty;

        @JsonProperty("hyphen-name")
        public Long getHyphenNameProperty() {
            return hyphenNameProperty;
        }

        public void setHyphenNameProperty(Long hyphenNameProperty) {
            this.hyphenNameProperty = hyphenNameProperty;
        }
    }

    @RequestMapping("/serveObjectWithHyphenNameProperty")
    public String serveObjectWithHyphenNameProperty(@RequestBody Class3 v) {
        return v.getHyphenNameProperty().toString();
    }
}
