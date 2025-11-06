# 🔒 Configuração de Segurança - Resumo Rápido

## ⚡ Configuração Rápida (5 minutos)

### 1. Criar arquivo `.env`
```bash
cp .env.example .env
```

### 2. Editar `.env` com seus valores
```env
DB_PASSWORD=SUA_SENHA_DO_BANCO
JWT_SECRET=$(openssl rand -base64 32)  # Gera chave aleatória
```

### 3. Para Linux/Mac - Exportar variáveis:
```bash
export $(cat .env | xargs)
mvn spring-boot:run
```

### 4. Para Windows (PowerShell):
```powershell
Get-Content .env | ForEach-Object {
    if ($_ -match '^([^#][^=]+)=(.*)$') {
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], 'Process')
    }
}
mvn spring-boot:run
```

---

## ✅ O que foi configurado?

### 1. **Variáveis de Ambiente**
- ✅ `.env.example` criado (template)
- ✅ `.gitignore` atualizado (`.env` não será commitado)
- ✅ `application.yml` usa variáveis de ambiente
- ✅ Valores padrão configurados (fallback)

### 2. **Validação de Senha Forte**
- ✅ Senha deve ter 8-128 caracteres
- ✅ Pelo menos: 1 maiúscula, 1 minúscula, 1 número, 1 especial
- ✅ Validação no DTO e no Service

### 3. **BCrypt para Senhas**
- ✅ Senhas são criptografadas antes de salvar
- ✅ Nunca armazenadas em texto plano
- ✅ Configurado no Spring Security

### 4. **CORS Configurável**
- ✅ Origens permitidas via variável de ambiente
- ✅ Headers e métodos configurados

### 5. **JWT Preparado**
- ✅ Configuração de secret e expiration no `application.yml`
- ✅ Classe `SecurityProperties` criada
- ⚠️ **TODO**: Implementar geração/validação de tokens (futuro)

---

## 📋 Variáveis de Ambiente Disponíveis

| Variável | Descrição | Padrão |
|----------|-----------|--------|
| `DB_HOST` | Host do banco de dados | `127.0.0.1` |
| `DB_PORT` | Porta do banco | `3306` |
| `DB_NAME` | Nome do banco | `faculdade` |
| `DB_USERNAME` | Usuário do banco | `root` |
| `DB_PASSWORD` | **Senha do banco** | `Developer123` |
| `JWT_SECRET` | **Chave secreta JWT** | `change-this...` |
| `JWT_EXPIRATION` | Expiração do token (ms) | `86400000` (24h) |
| `APP_PORT` | Porta da aplicação | `8080` |
| `CORS_ALLOWED_ORIGINS` | Origens permitidas | `http://localhost:5173,...` |

---

## ⚠️ IMPORTANTE - Segurança

### ❌ NUNCA FAÇA:
- ❌ Commitar arquivo `.env` com dados reais
- ❌ Colocar senhas no código
- ❌ Usar senhas fracas em produção
- ❌ Compartilhar chaves JWT

### ✅ SEMPRE FAÇA:
- ✅ Use `.env` para dados sensíveis
- ✅ Gere chaves JWT longas (mínimo 256 bits)
- ✅ Use senhas fortes (12+ caracteres)
- ✅ Use HTTPS em produção

---

## 🚀 Próximos Passos (Opcional)

1. **Implementar JWT** (quando necessário):
   - Adicionar dependência `jjwt` no `pom.xml`
   - Criar `JwtTokenProvider`
   - Criar `JwtAuthenticationFilter`
   - Atualizar `SecurityConfig`

2. **Rate Limiting**:
   - Limitar tentativas de login
   - Prevenir ataques de força bruta

3. **HTTPS em Produção**:
   - Configurar certificado SSL
   - Adicionar headers de segurança

---

## 📚 Documentação Completa

Veja `SECURITY.md` para documentação detalhada.

---

**Última atualização**: 2024

