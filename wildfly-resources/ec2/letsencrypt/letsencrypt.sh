curl -O http://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum -y install epel-release-latest-7.noarch.rpm
sudo yum -y install python2-certbot-nginx

sudo chown root:root ~/hkstlr.com.conf
sudo mkdir -p /etc/letsencrypt/configs
sudo cp ~/hkstlr.com.conf /etc/letsencrypt/configs/hkstlr.com.conf

sudo systemctl stop nginx

sudo certbot --nginx --config /etc/letsencrypt/configs/hkstlr.com.conf

sudo systemctl start nginx