package connector;

import java.io.IOException;

public interface Endpoint {

    void setProtocolHandler(ProtocolHandler protocolHandler);

    void init() throws IOException;

    void start() throws IOException;

    void bind() throws IOException;
}
