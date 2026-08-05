/* FitZone Web App Engine JS & Firebase Sync */
document.addEventListener('DOMContentLoaded', () => {
    // Theme Switcher Init
    const currentTheme = localStorage.getItem('fitzone_theme') || 'dark';
    document.documentElement.setAttribute('data-theme', currentTheme);

    const themeToggleBtn = document.getElementById('themeToggleBtn');
    if (themeToggleBtn) {
        themeToggleBtn.addEventListener('click', () => {
            const theme = document.documentElement.getAttribute('data-theme') === 'dark' ? 'light' : 'dark';
            document.documentElement.setAttribute('data-theme', theme);
            localStorage.setItem('fitzone_theme', theme);
        });
    }

    // Chart.js Revenue Chart initialization if canvas present
    const revCanvas = document.getElementById('revenueChart');
    if (revCanvas) {
        const ctx = revCanvas.getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug'],
                datasets: [{
                    label: 'Monthly Revenue (₹)',
                    data: [15000, 22000, 18500, 31000, 28000, 39000, 45000, 52000],
                    borderColor: '#f97316',
                    backgroundColor: 'rgba(249, 115, 22, 0.15)',
                    fill: true,
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: { labels: { color: '#94a3b8' } }
                },
                scales: {
                    x: { ticks: { color: '#94a3b8' }, grid: { color: 'rgba(255,255,255,0.05)' } },
                    y: { ticks: { color: '#94a3b8' }, grid: { color: 'rgba(255,255,255,0.05)' } }
                }
            }
        });
    }
});

// Firebase Cloud Sync Helper
window.syncToFirebaseCollection = async function(collectionName, dataObj) {
    if (window.fitzoneFirebase && window.fitzoneFirebase.db) {
        try {
            const docRef = await window.fitzoneFirebase.addDoc(
                window.fitzoneFirebase.collection(window.fitzoneFirebase.db, collectionName),
                { ...dataObj, timestamp: new Date() }
            );
            console.log(`🔥 Synced record to Firebase [${collectionName}] with ID:`, docRef.id);
        } catch (e) {
            console.warn(`Firebase sync warning for [${collectionName}]:`, e);
        }
    }
};
