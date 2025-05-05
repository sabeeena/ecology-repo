import {useState} from 'react';
import {Alert, Spinner, Card, Container} from 'react-bootstrap';
import api from '../api/axiosClient';
import DashboardFilter from '../components/DashboardFilter';
import HistoryChart    from '../components/HistoryChart';
import AqiInfoCard from "../components/AqiInfoCard";
import WeatherFilter from '../components/WeatherFilter';
import WeatherCard   from '../components/WeatherCard';

export default function Dashboard(){
    const [weather, setWeather]   = useState({temp:[], hum:[], press:[], wind:[]});
    const [wLoad,  setWLoad]      = useState(false);
    const [wErr,   setWErr]       = useState('');

    const loadWeather = async (f) => {
        setWLoad(true); setWErr('');
        try {
            const {data} = await api.post('/weather/dashboard', f);
            setWeather(data);
        } catch {
            setWErr('Ошибка загрузки погоды');
        }
        setWLoad(false);
    };


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

    return(
        <Container className="py-4">

            <h3 className="mb-3">Погода</h3>
            <p>Источник: <a href="https://openweathermap.org/current" target="_blank">OpenWeather</a>.</p>

            <Card className="mb-4 shadow-sm" style={{ backgroundColor: '#d0e7d2' }}>
                <Card.Body>
                    <WeatherFilter onSearch={loadWeather}/>
                </Card.Body>
            </Card>

            {wErr && <Alert variant="danger">{wErr}</Alert>}
            {wLoad && <Spinner/>}

            {weather.temp.length > 0 && (
                <>
                    <WeatherCard
                        icon="🌡️" title="Температура" unit="°C"
                        latest={weather.temp.at(-1) ? (weather.temp.at(-1).value - 273.15).toFixed(1) : ''}
                        points={weather.temp.map(p => ({
                            ts: p.ts,
                            value: p.value - 273.15
                        }))}
                        color="#E55C5C"
                        updatedAt={weather.temp.at(-1)?.ts}
                    />
                    <WeatherCard
                        icon="💧" title="Влажность" unit="%"
                        latest={weather.hum.at(-1)?.value ?? ''}
                        points={weather.hum}
                        color="#56bc7a"
                        updatedAt={weather.hum.at(-1)?.ts}
                    />
                    <WeatherCard
                        icon="🔽" title="Давление" unit=" гПа"
                        latest={weather.press.at(-1)?.value ?? ''}
                        points={weather.press}
                        color="#4a8cc7"
                        updatedAt={weather.press.at(-1)?.ts}
                    />
                    <WeatherCard
                        icon="🍃" title="Скорость ветра" unit=" м/с"
                        latest={weather.wind.at(-1)?.value ?? ''}
                        points={weather.wind}
                        color="#e28f41"
                        updatedAt={weather.wind.at(-1)?.ts}
                    />
                </>
            )}

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
        </Container>
    );
}
