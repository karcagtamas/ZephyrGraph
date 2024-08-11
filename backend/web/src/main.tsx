import ReactDOM from 'react-dom/client';
import { Provider } from 'react-redux';
import App from './App.tsx';
import './index.scss';
import { store } from './store/store.ts';
import EventsProvider from './providers/EventsProvider.tsx';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <Provider store={store}>
    <EventsProvider>
      <App />
    </EventsProvider>
  </Provider>
);
