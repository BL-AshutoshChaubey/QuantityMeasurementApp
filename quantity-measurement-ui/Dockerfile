# Build stage
FROM node:18-slim AS build
WORKDIR /app
COPY quantity-measurement-ui/package*.json ./
RUN npm install
COPY quantity-measurement-ui/ ./
RUN npm run build

# Production stage
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
# Copy a custom nginx config if needed (optional)
# COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
