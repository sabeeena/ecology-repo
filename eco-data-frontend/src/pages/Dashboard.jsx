import {useState} from 'react';
import {Alert,Spinner} from 'react-bootstrap';
import api from '../api/axiosClient';
import DashboardFilter from '../components/DashboardFilter';
import HistoryChart    from '../components/HistoryChart';

export default function Dashboard(){
    const [points,setPts]=useState([]), [poll,setPoll]=useState('aqi');
    const [loading,setL]=useState(false),[err,setErr]=useState('');

    const load=async f=>{
        setL(true); setErr(''); setPts([]);
        try{
            const {data}=await api.post('/air/dashboard',f);
            setPts(data); setPoll(f.pollutant);
        }catch{setErr('Ошибка загрузки');}
        setL(false);
    };

    return<>
        <h3 className="mb-3">Качество воздуха</h3>
        <DashboardFilter onSearch={load}/>
        {err && <Alert variant="danger">{err}</Alert>}
        {loading && <Spinner/>}
        {points.length>0 && <HistoryChart data={points} pollutant={poll}/>}
    </>;
}
