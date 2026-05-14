# Build stage
FROM node:18-slim AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . ./
RUN npm run build

# Production stage
FROM nginx:alpine
# Ensure Nginx serves the dist folder correctly
COPY --from=build /app/dist /usr/share/nginx/html
# Standard Nginx config is usually enough, but ensure it's healthy
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
