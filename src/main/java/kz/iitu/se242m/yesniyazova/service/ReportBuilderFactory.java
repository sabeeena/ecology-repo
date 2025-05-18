package kz.iitu.se242m.yesniyazova.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReportBuilderFactory {

    private final Map<String, ReportBuilder> impl =
            Map.of(
                    "xlsx", new XlsReportBuilder());

    public ReportBuilder make(String type) {
        return impl.getOrDefault(type.toLowerCase(), impl.get("xlsx"));
    }
}
