#!/bin/sh
CERTBOT_DOMAIN=www.hkstlr.com
WILDFLY_NEW_STORE_PASS=wildflyStorePass
WILDFLY_NEW_KEY_PASS=wildflyKeyPass
NEW_KEYSTORE_FILE=application
KEYSTORENAME=application
KEYSTOREALIAS=application
cd /opt/jboss/wildfly/standalone/configuration/
sudo openssl pkcs12 -export -in /etc/letsencrypt/live/$CERTBOT_DOMAIN/fullchain.pem -inkey /etc/letsencrypt/live/$CERTBOT_DOMAIN/privkey.pem -out $KEYSTORENAME.p12 -name $KEYSTOREALIAS
sudo keytool -importkeystore -deststorepass $WILDFLY_NEW_STORE_PASS -destkeypass $WILDFLY_NEW_KEY_PASS -destkeystore $NEW_KEYSTORE_FILE.jks -srckeystore $KEYSTORENAME.p12 -srcstoretype PKCS12 -alias $KEYSTOREALIAS
sudo chown jboss:jboss application.p12
sudo chown jboss:jboss application.jks
#
# AFAIK, this line in standalone.xml must be changed manually
# originally should be something like path="application.keystore"
# <ssl>
# <keystore path="application.jks" relative-to="jboss.server.config.dir" keystore-password="wildflyStorePass" alias="application" key-password="wildflyKeyPass"/>
# </ssl>
#
#