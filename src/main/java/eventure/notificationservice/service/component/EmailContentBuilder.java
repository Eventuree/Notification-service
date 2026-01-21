package eventure.notificationservice.service.component; // або service.impl

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class EmailContentBuilder {

    private final TemplateEngine templateEngine;

    public String buildHtmlContent(String templateName, Map<String, Object> variables) {
        return buildContent(templateName, ".html", variables);
    }

    public String buildTextContent(String templateName, Map<String, Object> variables) {
        return buildContent(templateName, ".txt", variables);
    }

    private String buildContent(String templateName, String suffix, Map<String, Object> variables) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process("mail/" + templateName + suffix, context);
    }
}
