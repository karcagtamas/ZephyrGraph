/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_VERSION: string;
  readonly VITE_API: string;
  readonly VITE_WORKSPACE: string;
  readonly VITE_WORKSPACE_FILE: string;
  readonly VITE_LANGUAGE_SERVER_PROTOCOL: 'ws' | 'wss';
  readonly VITE_LANGUAGE_SERVER_HOSTNAME: string;
  readonly VITE_LANGUAGE_SERVER_PORT: number;
  readonly VITE_LANGUAGE_SERVER_PATH: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
