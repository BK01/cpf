node ('master'){
  def gitProjectUrl = 'https://github.com/bcgov/cpf.git'

  def artifactoryServer = Artifactory.server 'prod'
  def mavenRuntime = Artifactory.newMavenBuild()
  def buildInfo

  stage ('SCM prepare') {
    dir (path: 'scm-checkout') {
      deleteDir()
      checkout([
        $class: 'GitSCM', 
        branches: [[name: 'refs/tags/${gitTag}']],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: gitProjectUrl]]
      ])
    }
  }

  stage ('Artifactory configuration') {
    mavenRuntime.tool = 'm3' 
    mavenRuntime.deployer releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local', server: artifactoryServer
    mavenRuntime.resolver releaseRepo: 'repo', snapshotRepo: 'repo', server: artifactoryServer
    mavenRuntime.deployer.deployArtifacts = false
    buildInfo = Artifactory.newBuildInfo()
  }

  stage ('Maven Install') {
    dir (path: 'scm-checkout') {
      mavenRuntime.run pom: 'pom.xml', goals: 'dependency:purge-local-repository clean install', buildInfo: buildInfo
    }
  }

  stage ('Artifactory Deploy') {
    dir (path: 'scm-checkout') {
      mavenRuntime.deployer.deployArtifacts buildInfo
      artifactoryServer.publishBuildInfo buildInfo
    }
  }
}

