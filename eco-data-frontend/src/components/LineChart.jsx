import { Line } from 'react-chartjs-2';
import { Chart as ChartJS, TimeScale, LinearScale, PointElement,
    LineElement, Tooltip, Legend } from 'chart.js';
import 'chartjs-adapter-luxon';

ChartJS.register(TimeScale, LinearScale, PointElement,
    LineElement, Tooltip, Legend);

export default function LineChart({ points, label, color = '#56bc7a' }) {
    const data = {
        labels: points.map(p => new Date(p.ts)),
        datasets: [{
            label,
            data: points.map(p => p.value),
            borderColor: color,
            backgroundColor: color + '33',
            fill: true,
            tension: .3,
            pointRadius: 0
        }]
    };

    const opts = {
        scales: {
            x: {
                type: 'time',
                time: { unit: 'day', tooltipFormat: 'dd.MM HH:mm' }
            },
            y: {
                beginAtZero: false,
                ticks: { precision: 0 }
            }
        },
        plugins: { legend: { display: false } },
        maintainAspectRatio: false
    };

    return <div style={{ height: 220 }}><Line data={data} options={opts} /></div>;
}
