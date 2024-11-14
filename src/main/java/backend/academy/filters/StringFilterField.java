package backend.academy.filters;

public sealed class StringFilterField extends FilterField<String> permits
    IpFilterField,
    UserFilterField,
    AgentFilterField,
    MethodFilterField,
    ProtocolFilterField,
    RefererFilterField,
    ResourceFilterField {
    @Override
    public void parseValue(String value) {
        super.value = value;
    }
}
