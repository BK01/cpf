// parameters
// ----------
// gitTag

def replace = { File source, String toSearch, String replacement ->
  source.write(source.text.replaceAll(toSearch, replacement))
}
    
def checkoutTag(folderName, url, tagName) {
  dir(folderName) {
    deleteDir()
    checkout([
       $class: 'GitSCM', 
        branches: [[name: "refs/tags/${tagName}"]],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: url]]
    ])
  }
}

def build(folderName, mavenRuntime, buildInfo) {
  dir (path: folderName) {
    mavenRuntime.run pom: 'pom.xml', goals: 'install', buildInfo: buildInfo
  }
}

def artifactoryDeploy(folderName, artifactoryServer, mavenRuntime, buildInfo) {
  dir (path: folderName) {
    mavenRuntime.deployer.deployArtifacts buildInfo
    artifactoryServer.publishBuildInfo buildInfo
  }
}

node ('master') {
  jdk = tool name: 'jdk-8'
  env.JAVA_HOME = "${jdk}"

  def artifactoryServer = Artifactory.server 'prod'
  def mavenRuntime = Artifactory.newMavenBuild()
  def buildInfo

  stage ('Artifactory configuration') {
    mavenRuntime.tool = 'm3' 
    mavenRuntime.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: artifactoryServer
    mavenRuntime.resolver releaseRepo: 'repo', snapshotRepo: 'repo', server: artifactoryServer
    mavenRuntime.deployer.deployArtifacts = false
    buildInfo = Artifactory.newBuildInfo()
  }

  stage ('Checkout') {
    checkoutTag('revolsys', 'ssh://git@github.com/revolsys/com.revolsys.open.git', 'CPF-${gitTag}');
    checkoutTag('cpf', 'ssh://git@github.com/revolsys/ca.bc.gov.open.cpf.git', '${gitTag}');
  }

  stage ('Maven Install') {
    build('revolsys', mavenRuntime, buildInfo);
    build('cpf', mavenRuntime, buildInfo);
  }

  stage ('Artifactory Deploy') {
    artifactoryDeploy('revolsys', artifactoryServer, mavenRuntime, buildInfo);
    artifactoryDeploy('cpf', artifactoryServer, mavenRuntime, buildInfo);
  }
}
