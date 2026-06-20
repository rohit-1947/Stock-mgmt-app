# ===== Stage 1 - Build Angular App =====
FROM node:20-alpine AS build-stage

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build             
# ✅ modern Angular uses this

# ===== Stage 2 - Serve with Nginx =====
FROM nginx:alpine              

COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=build-stage /app/dist/saas-app-ui/browser /usr/share/nginx/html  
# ✅ Angular 17+ adds /browser

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]  
# ✅ start nginx








# FROM node:21 AS build-stage

# WORKDIR /app

# COPY package*.json ./

# RUN npm install

# COPY . .

# RUN npm run build --prod

# #nginx proxy web server

# FROM nginx:apline

# COPY nginx.conf /etc/nginx/nginx.conf

# COPY --from=build-stage /app/dist/saas-app-ui /usr/share/nginx/html

# EXPOSE 80

