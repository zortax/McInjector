package de.zortax.mcinjector.instrumentation.mcp;// Created by leo on 29.05.18

import de.zortax.mcinjector.instrumentation.McAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class McpDownloadManager {

    public static final String MCP_SRG_LINK = "http://mcpbot.bspk.rs/mcp/%version%/mcp-%version%-srg.zip";
    public static final String MCP_MAPPINGS_LINK = "http://export.mcpbot.bspk.rs/mcp_stable/%release%-%version%/mcp_stable-%release%-%version%.zip";

    private static String version = null;
    private static String release = null;

    public static void downloadMappings(String version, String release) {
        McpDownloadManager.version = version;
        McpDownloadManager.release = release;

        File dir = new File(McAgent.getConfig().baseDir + McAgent.getConfig().mappingsPath + release + "-" + version + "/");
        if (dir.exists() && dir.isDirectory()) {
            McAgent.info("Mappings for " + version + " (" + release + ") already downloaded. Skipping...");
            return;
        }

        try {

            String mcpLink = MCP_MAPPINGS_LINK.replaceAll("%version%", version).replaceAll("%release%", release);
            String srgLink = MCP_SRG_LINK.replaceAll("%version%", version);

            McAgent.info("Downloading MCP mappings from " + mcpLink + " ...");

            URL mcpUrl = new URL(mcpLink);
            ReadableByteChannel rbc = Channels.newChannel(mcpUrl.openStream());
            FileOutputStream fos = new FileOutputStream("mcp-" + version + "-" + release + ".zip");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            McAgent.info("Downloading SRG mappings from " + srgLink + " ...");

            URL srgUrl = new URL(srgLink);
            rbc = Channels.newChannel(srgUrl.openStream());
            fos = new FileOutputStream("srg-" + version + ".zip");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

            if (!dir.exists() || !dir.isDirectory()) {
                if (!dir.mkdirs()) {
                    McAgent.warn("Couldn't create mappings directory...");
                    return;
                }
            }

            unzipFile("mcp-" + version + "-" + release + ".zip", dir.getPath() + "/mcp/");
            unzipFile("srg-" + version + ".zip", dir.getPath() + "/srg/");

            McAgent.info("Deleting downloaded ZIP files...");
            File mcpZip = new File("mcp-" + version + "-" + release + ".zip");
            File srgZip = new File("srg-" + version + ".zip");
            mcpZip.delete();
            srgZip.delete();

            McAgent.info("Done!");

        } catch (Exception e) {
            McAgent.warn("Failed to download mapping for " + version + " (" + release + ")!");
            if (McAgent.getConfig().log)
                e.printStackTrace();
        }

    }

    private static void unzipFile(String zipFile, String outputFolder){

        McAgent.info("Unzipping " + zipFile + " ...");

        byte[] buffer = new byte[1024];

        try{

            File folder = new File(outputFolder);
            if(!folder.exists()){
                if (!folder.mkdir()) {
                    McAgent.info("Couldn't create mappings directory...");
                    return;
                }
            }

            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                if (ze.isDirectory()) {
                    ze = zis.getNextEntry();
                    continue;
                }

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                McAgent.debug("Extracting "+ newFile.getPath() + " ...");
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            McAgent.info("Done unzipping " + zipFile);

        }catch(IOException ex){
            if (McAgent.getConfig().verbose)
                ex.printStackTrace();
        }
    }

    public static String getVersion() {
        return version;
    }

    public static String getRelease() {
        return release;
    }

}
