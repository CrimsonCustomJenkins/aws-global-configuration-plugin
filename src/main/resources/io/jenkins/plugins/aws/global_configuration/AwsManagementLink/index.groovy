package hudson.security.GlobalSecurityConfiguration

import hudson.security.SecurityRealm
import hudson.markup.MarkupFormatterDescriptor
import hudson.security.AuthorizationStrategy
import jenkins.AgentProtocol
import jenkins.model.GlobalConfiguration
import hudson.Functions
import hudson.model.Descriptor

def f=namespace(lib.FormTagLib)
def l=namespace(lib.LayoutTagLib)
def st=namespace("jelly:stapler")

l.layout(norefresh:true, permission:app.ADMINISTER, title:my.displayName, cssclass:request2.getParameter('decorate')) {
    l.main_panel {
        h1 {
            l.icon(src: "${resURL}/plugin/aws-global-configuration/images/aws.png", class: 'icon-xlg')
            text(my.displayName)
        }

        p()
        div(class:"behavior-loading", _("LOADING"))
        f.form(method:"post",name:"config",action:"configure") {
            set("instance",my);
            set("descriptor", my.descriptor);

            Functions.getSortedDescriptorsForGlobalConfig(my.FILTER).each { Descriptor descriptor ->
                set("descriptor",descriptor)
                set("instance",descriptor)
                f.rowSet(name:descriptor.jsonSafeClassName) {
                    st.include(from:descriptor, page:descriptor.globalConfigPage)
                }
            }

            f.bottomButtonBar {
                f.submit(value:_("Save"))
                f.apply()
            }
        }

        st.adjunct(includes: "lib.form.confirm")
    }
}