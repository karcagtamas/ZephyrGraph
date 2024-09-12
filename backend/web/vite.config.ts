import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react-swc';
import sass from 'sass';
import path from 'path';
import url from 'url';
import * as fs from 'fs';

// https://vitejs.dev/config/
export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '');
  return {
    server: {
      port: 3001,
      host: '0.0.0.0',
      proxy: {
        '/v1': {
          target: env.VITE_DEV_SERVER_URL,
          changeOrigin: true,
        },
      },
    },
    plugins: [react()],
    css: {
      preprocessorOptions: {
        scss: {
          implementation: sass,
        },
      },
    },
  };
});
