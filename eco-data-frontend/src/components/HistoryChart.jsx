import {Line} from 'react-chartjs-2';
import {
    Chart as ChartJS,
    CategoryScale, LinearScale,
    PointElement, LineElement,
    Tooltip, Legend
} from 'chart.js';
import dayjs from 'dayjs';

/* Регистрируем необходимые модули Chart.js */
ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Tooltip, Legend);

/**
 * Построение линий по списку точек [{ts, value}]
 * props:
 *  • data       – массив точек
 *  • pollutant  – строка (название показателя) – для подписи
 */
export default function HistoryChart({data=[], pollutant='aqi'}) {

    /* Подготовка данных для Chart.js */
    const chartData = {
        labels: data.map(p => dayjs(p.ts).format('DD.MM HH:mm')),
        datasets: [{
            label       : pollutant.toUpperCase(),
            data        : data.map(p => p.value),
            borderColor : '#56bc7a',   // pastel green (theme color)
            borderWidth : 2,
            tension     : 0.3,
            pointRadius : 2,
            fill        : false
        }]
    };

    const options = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: { beginAtZero: true }
        },
        plugins: { legend:{display:true}, tooltip:{mode:'index',intersect:false} }
    };

    return (
        <div style={{height:420}}>
            <Line data={chartData} options={options}/>
        </div>
    );
}
