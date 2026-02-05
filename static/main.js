document.addEventListener('DOMContentLoaded', function() {
    const getPromptBtn = document.getElementById('getPromptBtn');
    const promptDisplay = document.getElementById('promptDisplay');
    const loading = document.getElementById('loading');

    getPromptBtn.addEventListener('click', async function() {
        try {
            // Show loading state
            loading.style.display = 'block';
            promptDisplay.innerHTML = '';
            getPromptBtn.disabled = true;

            // Fetch random prompt from API
            const response = await fetch('/api/random');
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const data = await response.json();
            
            // Display the rich task information
            const formatClass = data.task_format === "Format?" ? "task-format-missing" : "task-format";
            const scenarioClass = data.scenario_category === "General" ? "task-scenario-missing" : "task-scenario";
            promptDisplay.innerHTML = `
                <div class="task-header">
                    <div class="task-left">
                        <div class="task-id">Task #${data.id}</div>
                        <div class="${formatClass}">${data.task_format}</div>
                    </div>
                    <div class="task-metadata">
                        <span class="task-category">${data.category}</span>
                        <span class="task-type">${data.task_type}</span>
                    </div>
                    <!--div class="task-scenario-compact">
                        <span class="scenario-label">Scenario:</span>
                        <span class="${scenarioClass}">${data.scenario_category}</span>
                    </div-->

                    <div class="task-scenario-compact">
                        <fieldset class="${scenarioClass}">
                            <legend>Scenario:</legend>
                            <span>${data.scenario_category}</span>
                        </fieldset>
                    </div>
                    
                    <!--fieldset class="task-scenario-compact ${scenarioClass}">
                        <legend>Scenario:</legend>
                        <span>${data.scenario_category}</span>
                    </fieldset-->
                </div>
                <div class="task-prompt">
                    <h3>Your TCF Prompt:</h3>
                    <p>${data.prompt}</p>
                </div>
            `;
            
        } catch (error) {
            console.error('Error fetching prompt:', error);
            promptDisplay.innerHTML = '<p class="error">Error loading prompt. Please try again.</p>';
        } finally {
            // Hide loading state and re-enable button
            loading.style.display = 'none';
            getPromptBtn.disabled = false;
        }
    });
}); 