### crudapi👋
crudapi is a combination of crud and api, which stands for create, delete, update and retrieve interface. It is a zero-code product by configuring. crudapi allows you to focus on your business, save a lot of money, and improve your work efficiency by eliminating the tedious process of crud code. crudapi aims to make working with data easier and is free for everyone! 

### 增删改查接口👋
crudapi是crud+api组合，表示增删改查接口，是一款零代码可配置的产品。使用crudapi可以告别枯燥无味的增删改查代码，让您更加专注业务，节约大量成本，从而提高工作效率。crudapi的目标是让处理数据变得更简单，所有人都可以免费使用！

### build
```bash
mvn clean install -Dmaven.test.skip=true

#aliyun mirror
mvn clean install -Dmaven.test.skip=true -s mirror-settings.xml
```

### downloadSources
```bash
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true

#aliyun mirror
mvn dependency:sources -DdownloadSources=true -DdownloadJavadocs=true -s mirror-settings.xml
```

### run
```bash
java -jar ./target/crudapi-0.0.4-SNAPSHOT.jar
```

### package
```bash
mvn clean package -Dmaven.test.skip=true
```

### GPG
```bash
gpg --gen-key

gpg --list-secret-key
gpg --list-key

gpg --delete-secret-keys
gpg --delete-keys

gpg --keyserver keyserver.ubuntu.com --send-keys KEY_ID
gpg --keyserver keyserver.ubuntu.com --recv-keys KEY_ID

gpg --list-signatures --keyid-format 0xshort

gpg --armor --export-secret-keys KEY_ID
gpg --armor --export KEY_ID
```

### deploy
```bash
export MAVEN_USERNAME=
export MAVEN_CENTRAL_TOKEN=
export MAVEN_GPG_PASSPHRASE=
mvn clean deploy -Dmaven.test.skip=true -s deploy-settings.xml
```
