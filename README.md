## GPU Radar 📡

An app dedicated to helping users find top deals for RTX 3 series cards. 

![demo](https://github.com/BurhanNaveed0/GPU-Radar/assets/81490717/6d729cb8-dd42-4707-8766-a9763027d493)

## Technologies Used 👨‍💻
<details>
  <summary>Client 📱</summary>
  <ul>
    <li><a href="https://www.java.com/en/">Java</a></li>
    <li><a href="https://expo.dev/">XML</a></li>
    <li><a href="https://developer.android.com/studio?gad_source=1&gclid=CjwKCAjw-O6zBhASEiwAOHeGxXeWZgT9muC50iZgfEeWoWRSc1p7O5V8lqIsRCIpYqx4VqIfEuMYvBoCR6AQAvD_BwE&gclsrc=aw.ds">Android Studio</a></li>
  </ul>
</details>

<details>
  <summary>Backend 📚</summary>
  <ul>
    <li><a href="https://www.python.org/">Python</a></li>
    <li><a href="https://github.com/thisbejim/Pyrebase">Pyrebase</a></li>
    <li><a href="https://firebase.google.com/">Firebase</a></li>
  </ul>
</details>

## Project Status ✔
Project complete; Git repo up to date. App version Rainforest API access expired; Android SDK/API Level outdated. 

## Project Demo / Code Reviews 📲

<ul>
    <li><a href="https://youtu.be/uYKIHIpRv1M">App Demo</a></li>
    <li><a href="https://youtu.be/Wfq9cpOj2pw">Frontend Code Review</a></li>
    <li><a href="https://youtu.be/lbJeoeuNTL0">Backend Code Review</a></li>
  </ul>

Front End 📱 (Android Studio + Java) <br /> 
![frontendgif](https://github.com/BurhanNaveed0/GPU-Radar/assets/81490717/da0ad10f-50ba-4775-a17b-77e2b6892eb2)

Back End 📚 (Firebase + Python) <br />
![backend](https://github.com/BurhanNaveed0/GPU-Radar/assets/81490717/4bd5cc3c-4f9d-4aee-b0e8-1de38d124361)

## Reflection 📝

  - What was the context for this project? (ie: was this a side project? was this for Turing? was this for an experiment?)
  - What did you set out to build?
  - Why was this project challenging and therefore a really good learning experience?
  - What were some unexpected obstacles?
  - What tools did you use to implement this project?
      - This might seem obvious because you are IN this codebase, but to all other humans now is the time to talk about why you chose webpack instead of create react app, or D3, or vanilla JS instead of a framework etc. Brag about your choices and justify them here.  

#### Example:  

This was a 3 week long project built during my third module at Turing School of Software and Design. Project goals included using technologies learned up until this point and familiarizing myself with documentation for new features.  

Originally I wanted to build an application that allowed users to pull data from the Twitter API based on what they were interested in, such as 'most tagged users'. I started this process by using the `create-react-app` boilerplate, then adding `react-router-4.0` and `redux`.  

One of the main challenges I ran into was Authentication. This lead me to spend a few days on a research spike into OAuth, Auth0, and two-factor authentication using Firebase or other third parties. Due to project time constraints, I had to table authentication and focus more on data visualization from parts of the API that weren't restricted to authenticated users.

At the end of the day, the technologies implemented in this project are React, React-Router 4.0, Redux, LoDash, D3, and a significant amount of VanillaJS, JSX, and CSS. I chose to use the `create-react-app` boilerplate to minimize initial setup and invest more time in diving into weird technological rabbit holes. In the next iteration I plan on handrolling a `webpack.config.js` file to more fully understand the build process.
