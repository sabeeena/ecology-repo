import {BrowserRouter,Routes,Route,Navigate} from 'react-router-dom';
import { useEffect, useState } from 'react';
import {Container} from 'react-bootstrap';
import NavBar   from './components/NavBar';
import Login    from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Profile   from './pages/Profile';
import Admin     from './pages/AdminPage';

export default function App() {
    const [token, setToken] = useState(localStorage.getItem('token'));
    const [role, setRole] = useState(localStorage.getItem('role'));

    useEffect(() => {
        const handleStorage = () => {
            setToken(localStorage.getItem('token'));
            setRole(localStorage.getItem('role'));
        };
        window.addEventListener('storage', handleStorage);
        return () => window.removeEventListener('storage', handleStorage);
    }, []);

    const Private = ({children,admin}) =>
        !token ? <Navigate to="/login"/> :
            admin && role!=='ADMIN' ? <Navigate to="/"/> :
                children;

    return (
        <BrowserRouter>
            <NavBar token={token} role={role} setToken={setToken} setRole={setRole} />
            <Container className="py-3">
                <Routes>
                    <Route path="/login" element={<Login setToken={setToken} setRole={setRole} />} />
                    <Route path="/register" element={<Register />} />

                    <Route path="/" element={<Dashboard />} />

                    <Route path="/profile" element={<Private><Profile setToken={setToken} setRole={setRole} /></Private>} />
                    <Route path="/admin" element={<Private admin><Admin /></Private>} />

                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </Container>
        </BrowserRouter>
    );
}
