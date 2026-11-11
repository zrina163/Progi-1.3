import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

const backend = {
  target: 'http://localhost:8080',
  changeOrigin: false, // backend mora vidjeti localhost:5173 (zbog Google redirecta i cookieja)
}

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/api': backend,
      '/oauth2': backend,
      '/login/oauth2': backend,
    },
  },
})