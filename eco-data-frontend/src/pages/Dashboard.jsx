import {useState} from 'react';
import {Alert,Spinner} from 'react-bootstrap';
import api from '../api/axiosClient';
import DashboardFilter from '../components/DashboardFilter';
import HistoryChart    from '../components/HistoryChart';
import AqiInfoCard from "../components/AqiInfoCard";
import { Card } from 'react-bootstrap';

export default function Dashboard(){
    const [points,setPts]=useState([]), [poll,setPoll]=useState('aqi');
    const [loading,setL]=useState(false),[err,setErr]=useState('');

    const load = async f => {
        setL(true);
        setErr('');
        setPts([]);
        try {
            const {data} = await api.post('/air/dashboard', f);
            setPts(data);
            setPoll(f.pollutant);
        } catch {
            setErr('Ошибка загрузки');
        }
        setL(false);
    };

    return<>
        <h3 className="mb-3">Загрязнение воздуха</h3>
        <AqiInfoCard/>
        <h4>Исторические данные о качестве воздуха</h4>
        <Card className="mb-4 shadow-sm" style={{ backgroundColor: '#d0e7d2' }}>
            <Card.Body>
                <DashboardFilter onSearch={load}/>
            </Card.Body>
        </Card>
        {err && <Alert variant="danger">{err}</Alert>}
        {loading && <Spinner/>}
        {points.length === 0 && !loading && !err && (
            <Alert variant="info">Нет данных по указанным параметрам</Alert>
        )}
        {points.length > 0 && (
            <Card className="mb-4 shadow-sm">
                <Card.Body>
                    <HistoryChart data={points} pollutant={poll} />
                </Card.Body>
            </Card>
        )}
    </>;
}
