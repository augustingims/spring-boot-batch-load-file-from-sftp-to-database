package com.teamdevsolution.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Output output = new Output();

    private final Input input = new Input();

    private final Sftp sftp = new Sftp();

    public Output getOutput() {
        return output;
    }

    public Sftp getSftp() {
        return sftp;
    }

    public Input getInput() {
        return input;
    }

    public static class Sftp {
        private String host;
        private Integer port;
        private String username;
        private String password;
        private Integer sessionTimeout;
        private Integer channelTimeout;
        private String dirRemote;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getSessionTimeout() {
            return sessionTimeout;
        }

        public void setSessionTimeout(Integer sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
        }

        public Integer getChannelTimeout() {
            return channelTimeout;
        }

        public void setChannelTimeout(Integer channelTimeout) {
            this.channelTimeout = channelTimeout;
        }

        public String getDirRemote() {
            return dirRemote;
        }

        public void setDirRemote(String dirRemote) {
            this.dirRemote = dirRemote;
        }
    }

    public static class Output {
        private String path;
        private String pathHisto;
        private String fileext;
        private String filename;
        private Integer timeout;
        private Integer retries;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getFileext() {
            return fileext;
        }

        public void setFileext(String fileext) {
            this.fileext = fileext;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }

        public Integer getRetries() {
            return retries;
        }

        public void setRetries(Integer retries) {
            this.retries = retries;
        }

        public String getPathHisto() {
            return pathHisto;
        }

        public void setPathHisto(String pathHisto) {
            this.pathHisto = pathHisto;
        }
    }

    public static class Input {
        private String path;
        private Integer chunkSize;
        private Integer linesToSkip;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public Integer getChunkSize() {
            return chunkSize;
        }

        public void setChunkSize(Integer chunkSize) {
            this.chunkSize = chunkSize;
        }

        public Integer getLinesToSkip() {
            return linesToSkip;
        }

        public void setLinesToSkip(Integer linesToSkip) {
            this.linesToSkip = linesToSkip;
        }
    }
}
