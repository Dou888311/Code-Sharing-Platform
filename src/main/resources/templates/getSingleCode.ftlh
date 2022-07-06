<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Code</title>
        <link rel="stylesheet"
               href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
        <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
        <script>hljs.initHighlightingOnLoad();</script>
    </head>
    <body>
        <span id="load_date">${date}</span>
        <br>
        <#if (timeRestraint)>
                        This snippet will be available for <span id="time_restriction">${time}</span> more seconds.
                    </#if>
                    <br>
                    <#if (viewsRestraint)>
                        Views left: <span id="views_restriction">${views}</span>.
                    </#if>
            <pre id='code_snippet'>
                <code>
                    ${code}
                </code>
            </pre>
    </body>
</html>