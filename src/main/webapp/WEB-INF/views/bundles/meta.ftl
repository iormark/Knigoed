<title>${title!""}</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://yastatic.net/jquery/3.1.1/jquery.min.js"></script>
<script src="/builds/script.js?4.1.0"></script>
<link rel="stylesheet" href="/builds/style.css?4.1.0">
<link rel="icon" href="/favicon.ico" type="image/x-icon">

<#if (meta.keywords)??><meta name="keywords" content="${meta.keywords}"></#if>
<#if (meta.description)??><meta name="description" content="${meta.description}"></#if>
<#if (meta.canonical)??><link rel="canonical" href="${meta.canonical}"></#if>
<#if (meta.robots)?? && meta.robots == "NOINDEX"><meta name="robots" content="noindex, nofollow"></#if>

<!--[htmlclean-protect]--><!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]--><!--[/htmlclean-protect]-->