// parameters
// ----------
// bcgovCpfBranch
// cpfVersion

node ('master') {
  def rtMaven = Artifactory.newMavenBuild()
  def buildInfo

  stage ('SCM globals') {
     sh '''
git config --global user.email "paul.austin@revolsys.com"
git config --global user.name "Paul Austin"
     '''
  }

  stage ('Checkout') {
    dir('bcgov-cpf') {
      deleteDir()
      checkout([
        $class: 'GitSCM',
        branches: [[name: '${bcgovCpfBranch}']],
        doGenerateSubmoduleConfigurations: false,
        extensions: [],
        gitTool: 'Default',
        submoduleCfg: [],
        userRemoteConfigs: [[url: 'ssh://git@github.com/revolsys/ca.bc.gov.open.cpf.git']]
      ])
    }
  }

  stage ('Set Project Versions') {
    def releaseVersion = "${cpfVersion}-RELEASE";
    dir('bcgov-cpf') {
      withMaven(jdk: 'jdk-8', maven: 'm3') {
        sh "mvn versions:set -DnewVersion='${releaseVersion}' -DgenerateBackupPoms=false"
      }
      sh 'sed -i "s/<ca.bc.gov.open.cpf.version>.*<\\/ca.bc.gov.open.cpf.version>/<ca.bc.gov.open.cpf.version>${releaseVersion}<\\/ca.bc.gov.open.cpf.version>/g" pom.xml'
    }
  }

  stage ('Tag') {
    def releaseVersion = "${cpfVersion}-RELEASE";
    def tagName = "BCGOV-${cpfVersion}-RELEASE";
    dir('bcgov-cpf') {
      sh """
git checkout -B '${bcgovCpfBranch}'
git commit -a -m "Version ${releaseVersion}"
git tag -f -a ${tagName} -m "Version ${releaseVersion}"
git push 'ssh://git@github.com/bcgov/cpf.git'
git push 'ssh://git@github.com/bcgov/cpf.git' ${tagName}
      """
    }
  }
}
