import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import sass from 'sass';
import { monaco } from '@bithero/monaco-editor-vite-plugin';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    react(),
    monaco({
      features: 'all',
      languages: ['kotlin'],
      globalAPI: true,
    }),
  ],
  css: {
    preprocessorOptions: {
      scss: {
        implementation: sass,
      },
    },
  },
});
