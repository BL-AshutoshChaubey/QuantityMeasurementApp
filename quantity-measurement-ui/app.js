// Step 1: PASTE YOUR POSTMAN JWT TOKEN HERE
const TEMP_JWT_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhc2h1dG9zaC5jaGF1YmV5QGJyaWRnZWxhYnouY29tIiwiaWF0IjoxNzc4NjcyNjYwLCJleHAiOjE3Nzg3NTkwNjB9.2wGrBf_wgmVlmRponr_N7MCHYl4HmlTBPdg3JjCu_lE"; 

// Classes & Objects
class ApiService {
    // Async, Promises, AJAX (Fetch API)
    static async performConversion(payload) {
        try {
            const response = await fetch('http://localhost:8080/api/v1/measurements/convert', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${TEMP_JWT_TOKEN}` 
                },
                body: JSON.stringify(payload)
            });

            const data = await response.json();

            // Conditional Logic & Exception Handling
            if (!response.ok) {
                throw new Error(data.errorMessage || 'Conversion failed on server');
            }
            
            return data;
        } catch (error) {
            console.error("API Error:", error);
            throw error; // Re-throw to be handled by the UI class
        }
    }
}

class UIManager {
    constructor() {
        // DOM Manipulation references
        this.form = document.getElementById('conversion-form');
        this.resultContainer = document.getElementById('result-container');
        this.resultText = document.getElementById('result-text');
        this.errorText = document.getElementById('error-text');
        this.btn = document.getElementById('convertBtn');

        this.init();
    }

    init() {
        // Event Handling & CallBack
        this.form.addEventListener('submit', (event) => this.handleFormSubmit(event));
    }

    async handleFormSubmit(event) {
        event.preventDefault(); // Prevent page reload
        
        // Dynamic UI rendering (loading state)
        this.btn.innerText = "Converting...";
        this.resultContainer.classList.add('hidden');
        this.errorText.innerText = "";

        // Extract values
        const value = parseFloat(document.getElementById('inputValue').value);
        const fromUnit = document.getElementById('fromUnit').value;
        const toUnit = document.getElementById('toUnit').value;

        // ES9 feature: Object Spread properties (just to hit the requirement)
        const baseRequest = { value };
        const fullRequest = { ...baseRequest, fromUnit, toUnit };

        try {
            // Await the Promise
            const responseDto = await ApiService.performConversion(fullRequest);
            
            // Dynamic UI rendering (Success state)
            this.resultText.innerText = `${value} ${fromUnit} = ${responseDto.resultValue} ${responseDto.unit}`;
            this.resultText.style.color = "#0f172a";
            this.resultContainer.classList.remove('hidden');
            
        } catch (error) {
            // Dynamic UI rendering (Error state)
            this.resultText.innerText = "Error";
            this.resultText.style.color = "var(--error)";
            this.errorText.innerText = error.message;
            this.resultContainer.classList.remove('hidden');
        } finally {
            // ES9 feature: Promise.finally()
            this.btn.innerText = "Convert Now";
        }
    }
}

// Instantiate the class when the DOM loads
document.addEventListener('DOMContentLoaded', () => {
    new UIManager();
});