import { MonacoLanguageClient } from 'monaco-languageclient';
import {
  WebSocketMessageReader,
  WebSocketMessageWriter,
  toSocket,
} from 'vscode-ws-jsonrpc';
import { CloseAction, ErrorAction } from 'vscode-languageclient';

const LS_WS_URL = 'ws://localhost:8080/ws/language-server/kotlin';

export const connectToLs = () => {
  return new Promise((resolve, reject) => {
    const webSocket = new WebSocket(LS_WS_URL);

    webSocket.onopen = () => {
      console.log('LS WebSocket connection Open');
      const socket = toSocket(webSocket);
      const reader = new WebSocketMessageReader(socket);
      const writer = new WebSocketMessageWriter(socket);
      const languageClient = new MonacoLanguageClient({
        name: `Kotlin Language Client`,
        clientOptions: {
          documentSelector: ['kotlin'],
          errorHandler: {
            error: () => ({ action: ErrorAction.Continue }),
            closed: () => ({ action: CloseAction.DoNotRestart }),
          },
        },
        connectionProvider: {
          get: () => Promise.resolve({ reader, writer }),
        },
      });

      languageClient
        .start()
        .then(() => {
          resolve(languageClient);
        })
        .catch((err) => {
          console.error(err);
        });
    };

    webSocket.onerror = (error) => {
      console.log('LS WebSocket connection Error');
      reject(error);
    };
  });
};
