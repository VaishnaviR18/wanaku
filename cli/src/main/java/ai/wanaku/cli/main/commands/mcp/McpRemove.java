package ai.wanaku.cli.main.commands.mcp;

import ai.wanaku.cli.main.commands.BaseCommand;
import ai.wanaku.cli.main.support.WanakuPrinter;
import ai.wanaku.core.forward.discovery.client.AutoDiscoveryClient;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import picocli.CommandLine.Command;

import org.jline.terminal.Terminal;

import static ai.wanaku.cli.main.support.ResponseHelper.commonResponseErrorHandler;
import static picocli.CommandLine.Option;

@Command(name = "remove",
        description = "Remove Mcp server")
public class McpRemove extends BaseCommand {
    @Option(names = {"--host"}, description = "The API host", defaultValue = "http://localhost:8080",
            arity = "0..1")
    protected String host;

    @Option(names = {"--name"}, description = "The name of the mcp server to remove ", required = true, arity = "0..1")
    protected String name;


    @Override
    public Integer doCall(Terminal terminal, WanakuPrinter printer) throws Exception {
        AutoDiscoveryClient discoverClient = new AutoDiscoveryClient(host, name, "", "");

        try {
            discoverClient.deregister();
        } catch (WebApplicationException ex) {
            Response response = ex.getResponse();
            commonResponseErrorHandler(response);
            return EXIT_ERROR;
        }
        return EXIT_OK;
    }
}
