// parameters
// ----------
// rsBranch
// cpfBranch
// gitTag

def replace = { File source, String toSearch, String replacement ->
  source.write(source.text.replaceAll(toSearch, replacement))
}
    
def checkoutBranch(folderName, url, branchName) {
  dir(folderName) {
    deleteDir()
    checkout([
      $class: 'GitSCM',
      branches: [[name: branchName]],
      doGenerateSubmoduleConfigurations: false,
      extensions: [],
      gitTool: 'Default',
      submoduleCfg: [],
      userRemoteConfigs: [[url: url]]
    ])
  }
}

def setVersion(folderName, prefix) {
  def tagName = "${gitTag}";
  if (prefix != null) {
    tagName = "${prefix}-${gitTag}";
  }
  dir(folderName) {
    sh "git checkout -B version-${tagName}"
    withMaven(jdk: 'jdk-8', maven: 'm3') {
      sh "mvn versions:set -DnewVersion='${tagName}' -DgenerateBackupPoms=false"
    }
    sh 'sed -i "s/<com.revolsys.open.version>.*<\\/com.revolsys.open.version>/<com.revolsys.open.version>CPF-${gitTag}<\\/com.revolsys.open.version>/g" pom.xml'
  }
}

def tagVersion(folderName, prefix) {
  def tagName = "${gitTag}";
  if (prefix != null) {
    tagName = "${prefix}-${gitTag}";
  }
  dir(folderName) {
    sh """
git commit -a -m "Version ${tagName}"
git tag -f -a ${tagName} -m "Version ${tagName}"
git push origin ${tagName}
    """
  }
}

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
    checkoutBranch('revolsys', 'ssh://git@github.com/revolsys/com.revolsys.open.git', '${rsBranch}');
    checkoutBranch('cpf', 'ssh://git@github.com/revolsys/ca.bc.gov.open.cpf.git', '${cpfBranch}');
  }

  stage ('Set Project Versions') {
    setVersion('revolsys', 'CPF');
    setVersion('cpf', null);
  }

  stage ('Tag') {
    tagVersion('revolsys', 'BCDEM');
    tagVersion('cpf', null);
  }
}
