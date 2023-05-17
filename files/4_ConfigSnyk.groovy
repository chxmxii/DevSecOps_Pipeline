import hudson.model.*
import jenkins.model.*
import hudson.tools.*
import hudson.tasks.*
import io.snyk.jenkins.tools.*

def instance = Jenkins.getInstance()
def snyk_name = "SnykLatest"
def snyk_home = ""
def snyk_installer = new SnykInstaller("", "latest", 24L, null)
def snyk_properties = new InstallSourceProperty([snyk_installer])

println("[init.groovy.d] START Configuring Snyk Installation...")
// Get the GlobalConfiguration descriptor of Snyk plugin.
def snyk_conf = instance.getDescriptor("io.snyk.jenkins.tools.SnykInstallation")

def snyk_inst = new SnykInstallation(
    snyk_name,
    snyk_home,
    [snyk_properties]
)

// Only add the new Snyk setting if it does not exist - do not overwrite existing config
def snyk_installations = snyk_conf.getInstallations()

def snyk_inst_exists = false
snyk_installations.each {
    installation = (SnykInstallation) it
    if (snyk_inst.getName() == installation.getName()) {
        snyk_inst_exists = true
        println("Found existing installation: " + installation.getName())
    }
}
if (!snyk_inst_exists) {
    snyk_installations += snyk_inst
    snyk_conf.setInstallations((SnykInstallation[]) snyk_installations)
    snyk_conf.save()
}


// Save the state
instance.save()

println("[init.groovy.d] END")