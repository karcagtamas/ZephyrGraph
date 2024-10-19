import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import './App.scss';
import TabView from './frame/TabView';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TabView />
    </QueryClientProvider>
  );
}

export default App;
