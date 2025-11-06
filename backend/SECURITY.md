# 🔒 Guia de Segurança - Spring Security

Este documento explica como configurar e manter a segurança da aplicação.

## 📋 Índice
1. [Variáveis de Ambiente](#variáveis-de-ambiente)
2. [Senhas](#senhas)
3. [JWT (JSON Web Tokens)](#jwt)
4. [Boas Práticas](#boas-práticas)
5. [Configuração Inicial](#configuração-inicial)

---

## 🌍 Variáveis de Ambiente

### Por que usar variáveis de ambiente?

**NUNCA** coloque senhas, chaves secretas ou credenciais diretamente no código ou no `application.yml`. Sempre use variáveis de ambiente para:

- ✅ Segurança: evita que dados sensíveis sejam commitados no Git
- ✅ Flexibilidade: diferentes configurações para dev/staging/produção
- ✅ Boas práticas: padrão da indústria

### Como configurar

1. **Crie um arquivo `.env` na raiz do projeto** (baseado no `.env.example`):

```bash
cp .env.example .env
```

2. **Edite o `.env` com seus valores reais**:

```env
DB_PASSWORD=SUA_SENHA_DO_BANCO_AQUI
JWT_SECRET=UMA_CHAVE_SECRETA_MUITO_LONGA_E_ALEATORIA_AQUI
```

3. **O Spring Boot lê automaticamente** variáveis de ambiente do sistema operacional.

### Para desenvolvimento local (Linux/Mac):

```bash
# Opção 1: Exportar no terminal antes de rodar
export DB_PASSWORD="minhasenha123"
export JWT_SECRET="minha-chave-secreta-super-longa"
mvn spring-boot:run

# Opção 2: Criar arquivo .env e usar um loader
# (Recomendado usar spring-dotenv ou similar)
```

### Para produção:

Use o sistema de variáveis de ambiente do seu servidor/hospedagem:
- **Heroku**: `heroku config:set JWT_SECRET=valor`
- **AWS**: Console AWS → EC2 → User Data ou Systems Manager
- **Docker**: `docker run -e JWT_SECRET=valor ...`
- **Kubernetes**: ConfigMaps e Secrets

---

## 🔐 Senhas

### Validação de Senha Forte

A aplicação valida senhas com os seguintes requisitos:

- ✅ **Mínimo 8 caracteres, máximo 128**
- ✅ **Pelo menos 1 letra maiúscula** (A-Z)
- ✅ **Pelo menos 1 letra minúscula** (a-z)
- ✅ **Pelo menos 1 número** (0-9)
- ✅ **Pelo menos 1 caractere especial** (@$!%*?&)

**Exemplo de senha válida**: `MinhaSenh@123`

### Como as senhas são armazenadas?

As senhas **NUNCA** são armazenadas em texto plano. Elas são:

1. **Validadas** antes de serem salvas
2. **Criptografadas** com **BCrypt** (algoritmo de hash unidirecional)
3. **Armazenadas** no banco de dados como hash

**BCrypt** é um algoritmo seguro que:
- ✅ Gera um hash diferente a cada vez (mesmo para a senha igual)
- ✅ É resistente a ataques de força bruta
- ✅ É o padrão recomendado pelo Spring Security

### Como funciona a verificação?

Quando um usuário faz login:
1. A senha digitada é comparada com o hash armazenado
2. O BCrypt verifica se a senha corresponde ao hash
3. Se corresponder, o login é bem-sucedido

---

## 🎫 JWT (JSON Web Tokens)

### O que é JWT?

JWT é um padrão para autenticação stateless (sem estado). Quando o usuário faz login, recebe um **token** que deve ser enviado em todas as requisições subsequentes.

### Estrutura do Token

```
HEADER.PAYLOAD.SIGNATURE
```

- **Header**: Tipo do token e algoritmo
- **Payload**: Dados do usuário (ID, email, permissões)
- **Signature**: Assinatura para garantir autenticidade

### Como funciona?

```
1. Usuário faz login → Recebe token JWT
2. Usuário envia token no header: Authorization: Bearer <token>
3. Servidor valida token → Permite acesso
4. Token expira após X horas → Usuário precisa fazer login novamente
```

### Configuração JWT

Atualmente, o JWT está como **TODO** no código. Para implementar:

1. **Adicione a dependência JWT no `pom.xml`** (veja abaixo)
2. **Configure a chave secreta** no `.env`:
   ```env
   JWT_SECRET=SUA_CHAVE_SECRETA_SUPER_LONGA_MINIMO_256_BITS
   ```
3. **A chave deve ser longa e aleatória** (use um gerador seguro)

**⚠️ IMPORTANTE**: 
- A chave JWT deve ter **no mínimo 256 bits** (32 caracteres)
- Use um gerador seguro: `openssl rand -base64 32`
- **NUNCA** compartilhe ou commite a chave secreta

---

## ✅ Boas Práticas

### 1. **Senhas Fortes**
- ✅ Use senhas com 12+ caracteres
- ✅ Combine letras, números e símbolos
- ✅ Evite palavras comuns ou informações pessoais
- ✅ Use gerenciadores de senha (LastPass, 1Password, etc.)

### 2. **Variáveis de Ambiente**
- ✅ Sempre use `.env` para dados sensíveis
- ✅ Adicione `.env` ao `.gitignore`
- ✅ Use `.env.example` como template (sem valores reais)
- ✅ Nunca commite arquivos `.env` com dados reais

### 3. **Chaves e Secrets**
- ✅ Use chaves longas e aleatórias (mínimo 256 bits)
- ✅ Gere chaves com ferramentas seguras: `openssl rand -base64 32`
- ✅ Rotacione chaves periodicamente em produção
- ✅ Use diferentes chaves para dev/staging/produção

### 4. **HTTPS em Produção**
- ✅ Sempre use HTTPS em produção (não HTTP)
- ✅ Configure certificados SSL/TLS
- ✅ Configure HSTS (HTTP Strict Transport Security)

### 5. **Headers de Segurança**
Em produção, adicione headers de segurança:
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`
- `Strict-Transport-Security: max-age=31536000`

### 6. **Rate Limiting**
- ✅ Implemente rate limiting para prevenir ataques de força bruta
- ✅ Limite tentativas de login (ex: 5 tentativas por 15 minutos)

### 7. **Logs e Monitoramento**
- ✅ Registre tentativas de login falhas
- ✅ Monitore atividades suspeitas
- ✅ Não logue senhas ou tokens em produção

---

## 🚀 Configuração Inicial

### Passo a passo:

1. **Copie o arquivo de exemplo**:
   ```bash
   cp .env.example .env
   ```

2. **Configure as variáveis no `.env`**:
   ```env
   DB_PASSWORD=SUA_SENHA_DO_BANCO
   JWT_SECRET=$(openssl rand -base64 32)
   ```

3. **Gere uma chave JWT segura** (Linux/Mac):
   ```bash
   openssl rand -base64 32
   ```
   Cole o resultado no `.env` como `JWT_SECRET`

4. **Exporte as variáveis** (Linux/Mac):
   ```bash
   export $(cat .env | xargs)
   mvn spring-boot:run
   ```

5. **Ou use um plugin Maven** (recomendado):
   Adicione no `pom.xml`:
   ```xml
   <plugin>
       <groupId>com.github.ekryd.sortpom</groupId>
       <artifactId>sortpom-maven-plugin</artifactId>
   </plugin>
   ```

### Para Docker:

```dockerfile
ENV DB_PASSWORD=${DB_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
```

### Para Kubernetes:

Use Secrets:
```yaml
apiVersion: v1
kind: Secret
metadata:
  name: app-secrets
data:
  jwt-secret: <base64-encoded-value>
```

---

## 📚 Referências

- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [JWT.io](https://jwt.io/)
- [BCrypt Generator](https://bcrypt-generator.com/)

---

## ⚠️ Checklist de Segurança

Antes de fazer deploy em produção:

- [ ] Todas as senhas estão em variáveis de ambiente
- [ ] `.env` está no `.gitignore`
- [ ] Chave JWT tem pelo menos 256 bits
- [ ] HTTPS está configurado
- [ ] Headers de segurança estão configurados
- [ ] Rate limiting está implementado
- [ ] Logs não contêm informações sensíveis
- [ ] Backups do banco de dados estão configurados
- [ ] Firewall está configurado
- [ ] Monitoramento está ativo

---

**Última atualização**: 2024

