(function () {
  // PUBLIC_INTERFACE
  /** Initialize SmartHome_Home screen behaviors:
   * - Renders mock device data into the five Figma cards
   * - Enables D-Pad (Arrow) navigation across cards with focus ring
   * - Toggles device state with Enter/Space/DPAD_CENTER, updates visuals
   * - Minimal tab switcher for Home (cards), Activity, Settings via sidebar icons
   */
  const devices = [
    { id: 'dev1', name: 'Living Room Light', type: 'light', on: true },
    { id: 'dev2', name: 'Living Room Fan', type: 'fan', on: false },
    { id: 'dev3', name: 'Dining Room Fan', type: 'fan', on: false },
    { id: 'dev4', name: 'Bedroom 1 Light', type: 'light', on: false },
    { id: 'dev5', name: 'Bedroom 1 AC', type: 'ac', on: true }
  ];

  const $ = (id) => document.getElementById(id);

  function applyState(card, isOn, titleEl) {
    card.classList.toggle('is-on', isOn);
    card.classList.toggle('is-off', !isOn);
    if (titleEl) {
      const text = titleEl.dataset.base || titleEl.textContent || '';
      // Persist the base text for future toggles
      if (!titleEl.dataset.base) titleEl.dataset.base = text.replace(/\s+•\s+(On|Off)$/i, '');
      titleEl.textContent = (titleEl.dataset.base || text) + ' • ' + (isOn ? 'On' : 'Off');
    }
  }

  function updateDevicesCount() {
    // Show total device count per requirement
    const countNumber = $('count-number');
    if (countNumber) {
      countNumber.textContent = String(devices.length);
    }
  }

  function setupCards() {
    const cards = [];
    for (let i = 1; i <= 5; i++) {
      const container = $(`card${i}-container`);
      const titleEl = $(`card${i}-title`);
      const switchEl = $(`card${i}-switch`);

      if (!container || !titleEl || !switchEl) continue;

      container.classList.add('card');
      container.setAttribute('tabindex', '0');
      container.setAttribute('role', 'button');
      container.dataset.index = String(i - 1);

      // Bind device data to the card
      const dev = devices[i - 1];
      if (dev) {
        titleEl.textContent = `${dev.name}`;
        titleEl.dataset.base = dev.name;
        applyState(container, !!dev.on, titleEl);
      }

      // Click/tap toggling (if applicable on TV remotes)
      container.addEventListener('click', () => {
        const idx = Number(container.dataset.index || '0');
        devices[idx].on = !devices[idx].on;
        applyState(container, devices[idx].on, titleEl);
        updateDevicesCount();
      });

      cards.push(container);
    }
    return cards;
  }

  function setupSidebarTabs() {
    const home = $('home-group');
    const history = $('history-comp'); // Activity
    const settings = $('settings-comp');

    const homeContent = $('home-content');
    const activityContent = $('activity-content');
    const settingsContent = $('settings-content');

    function showTab(tab) {
      // Toggle visibility of sections
      if (homeContent) homeContent.classList.toggle('hidden', tab !== 'home');
      if (activityContent) activityContent.classList.toggle('hidden', tab !== 'activity');
      if (settingsContent) settingsContent.classList.toggle('hidden', tab !== 'settings');

      // Devices subheader visibility is needed only for Home
      const subheader = $('subheader-container');
      if (subheader) subheader.style.display = (tab === 'home') ? 'block' : 'none';

      // Highlight Home background when Home is active
      const homeBg = $('home-bg');
      if (homeBg) homeBg.style.opacity = (tab === 'home') ? '1' : '0.4';
    }

    function activateFromEl(el) {
      const tab = el && el.getAttribute('data-tab');
      if (tab) showTab(tab);
    }

    [home, history, settings].forEach(el => {
      if (!el) return;
      el.addEventListener('click', () => activateFromEl(el));
      el.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' || e.keyCode === 13 || e.code === 'Enter' || e.keyCode === 23 || e.key === ' ') {
          e.preventDefault();
          activateFromEl(el);
        }
      });
    });

    // Default tab
    showTab('home');
  }

  function setupDPADNavigation(cards) {
    if (!cards || cards.length === 0) return;

    // Grid neighbor mapping: indexes 0..4 laid out as:
    // 0 1 2
    // 3 4
    const neighbors = {
      0: { right: 1, down: 3 },
      1: { left: 0, right: 2, down: 4 },
      2: { left: 1 },
      3: { right: 4, up: 0 },
      4: { left: 3, up: 1 }
    };

    let currentIndex = 0;
    const focusCard = (idx) => {
      if (idx < 0 || idx >= cards.length) return;
      currentIndex = idx;
      const el = cards[currentIndex];
      if (el) {
        el.focus();
      }
    };

    // Start focus on first card
    focusCard(0);

    document.addEventListener('keydown', (e) => {
      const active = document.activeElement;
      const isCard = active && active.classList && active.classList.contains('card');
      if (!isCard) return;

      const idx = Number(active.dataset.index || '0');
      const map = neighbors[idx] || {};

      if (e.key === 'ArrowRight') {
        if (map.right !== undefined) {
          e.preventDefault();
          focusCard(map.right);
        }
      } else if (e.key === 'ArrowLeft') {
        if (map.left !== undefined) {
          e.preventDefault();
          focusCard(map.left);
        }
      } else if (e.key === 'ArrowDown') {
        if (map.down !== undefined) {
          e.preventDefault();
          focusCard(map.down);
        }
      } else if (e.key === 'ArrowUp') {
        if (map.up !== undefined) {
          e.preventDefault();
          focusCard(map.up);
        }
      } else if (
        e.key === 'Enter' ||
        e.keyCode === 13 ||
        e.code === 'Enter' ||
        e.keyCode === 23 ||     // Android TV DPAD_CENTER
        e.key === ' '
      ) {
        e.preventDefault();
        const device = devices[idx];
        if (!device) return;
        device.on = !device.on;

        const titleEl = document.getElementById(`card${idx + 1}-title`);
        applyState(active, device.on, titleEl);
        updateDevicesCount();
      }
    });
  }

  document.addEventListener('DOMContentLoaded', () => {
    const cards = setupCards();
    updateDevicesCount();
    setupSidebarTabs();
    setupDPADNavigation(cards);
  });
})();
