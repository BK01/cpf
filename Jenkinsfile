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
		dir('bcgov-cpf') {
			withMaven(jdk: 'jdk-8', maven: 'm3') {
				sh "mvn versions:set -DnewVersion='${cpfVersion}' -DgenerateBackupPoms=false"
			}
			sh 'sed -i "s/<ca.bc.gov.open.cpf.version>.*<\\/ca.bc.gov.open.cpf.version>/<ca.bc.gov.open.cpf.version>${cpfVersion}-RELEASE<\\/ca.bc.gov.open.cpf.version>/g" pom.xml'
		}
  }

  stage ('Tag') {
		def bcgovCpfVersion = "${cpfVersion}-BCGOV";
		dir('bcgov-cpf') {
			sh """
git commit -a -m "Version ${bcgovCpfVersion}"
git tag -f -a ${bcgovCpfVersion} -m "Version ${bcgovCpfVersion}"
git push 'ssh://git@github.com/bcgov/cpf.git'
git push 'ssh://git@github.com/bcgov/cpf.git' ${bcgovCpfVersion}
			"""
		}
  }
}
