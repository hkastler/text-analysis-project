sudo chown root:root ~/nginx.conf &&
sudo chown root:root ~/jboss.conf &&
sudo mv ~/nginx.conf /etc/nginx/nginx.conf &&
sudo mv ~/jboss.conf /etc/nginx/conf.d/jboss.conf &&
#sudo service nginx start
sudo systemctl start nginx