package ai.wanaku.cli.main.commands.mcp;

import ai.wanaku.cli.main.commands.BaseCommand;
import ai.wanaku.cli.main.support.WanakuPrinter;
import ai.wanaku.core.forward.discovery.client.AutoDiscoveryClient;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.jline.terminal.Terminal;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static ai.wanaku.cli.main.support.ResponseHelper.commonResponseErrorHandler;
import static picocli.CommandLine.Option;

@Command(name = "create",
        description = "Create mcp server")
public class McpCreate extends BaseCommand {
    @Option(names = {"--name"}, description = "The name of the service to create", required = true, arity = "0..1")
    protected String name;

    @Option(names = {"--host"}, description = "The API host", defaultValue = "http://localhost:8080",
            arity = "0..1")
    protected String host;

    @CommandLine.Option(names = {"-N", "--namespace"}, description="The namespace associated with the service", defaultValue = "", required = true)
    private String namespace;

    @Option(names = {"--announceAddress"}, description = "The Mcp announce/forward address", defaultValue = "http://localhost:8180/mcp/sse",
            arity = "0..1")
    protected String announceAddress;

    @Override
    public Integer doCall(Terminal terminal, WanakuPrinter printer) throws  Exception {
        AutoDiscoveryClient discoverClient = new AutoDiscoveryClient(host, name, namespace, announceAddress);

        try {
            discoverClient.register();
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            commonResponseErrorHandler(response);
            return EXIT_ERROR;
        }
        return EXIT_OK;
    }
}
